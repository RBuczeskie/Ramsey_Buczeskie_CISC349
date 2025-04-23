import json
from pymongo import MongoClient
from bson.objectid import ObjectId
from flask import Flask
from flask import request
from flask.json import jsonify
import certifi

app = Flask(__name__)

client = MongoClient('mongodb+srv://ramseyjbuczeskie47:34035605@cisc349.aqymd.mongodb.net/?retryWrites=true&w=majority&appName=CISC349', tlsCAFile=certifi.where())

db = client["CISC349final"]
collection = db["example_collection"]

@app.route('/')
def index():
    return "<h1>Welcome to our server!!</h1>"

@app.route("/add_item", methods=["POST"])
def add_item():
    content = request.get_json()
    _id = collection.insert_one(content)
    return json.dumps({'id' : str(_id.inserted_id)})
    
@app.route('/get_items', methods=['GET'])
def image_list():
    items = list(collection.find())
    print(f"Got {len(items)} items.")
    return json.dumps(items, default=str)
    
@app.route('/delete_item', methods=['PUT'])
def delete_item():
    collection.delete_one({"_id:" : ObjectId(request.args.get("id"))})
    print("Deleted item.")
    return request.args.get("id")
    

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)