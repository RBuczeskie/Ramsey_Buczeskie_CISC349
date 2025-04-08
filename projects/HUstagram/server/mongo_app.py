import json
from pymongo import MongoClient
from bson.objectid import ObjectId
from flask import Flask
from flask import request
from flask.json import jsonify
import certifi

app = Flask(__name__)

client = MongoClient('mongodb+srv://ramseyjbuczeskie47:34035605@cisc349.aqymd.mongodb.net/?retryWrites=true&w=majority&appName=CISC349', tlsCAFile=certifi.where())

db = client["CISC349"]

@app.route('/')
def index():
    return "<h1>Welcome to our server!!</h1>"

#Add user
@app.route('/add', methods=['POST'])
def add():
#def add(name, address):
    collection = db["customers"]
    request_data = request.get_json()
    
    name = request_data['name']
    address = request_data['address']
    phone = request_data['phone']
    
    print(f'Name: {name}, Address: {address}, Phone: {phone}')
    #data = {"name":name, "address":address}
    _id = collection.insert_one(request_data)
    return json.dumps({'id':str(_id.inserted_id)})
    
#Update user
@app.route('/update', methods=['POST'])
def update():
    collection = db["customers"]
    request_data = request.get_json()
    
    id = request_data['_id']
    name = request_data['name']
    address = request_data['address']
    phone = request_data['phone']
    comments = request_data['comments']
    
    print(f'ID: {id}, Name: {name}, Address: {address}, Phone: {phone}, Comments: {comments}')
    filter = {'_id':id}
    newvalues = {"$push": {'comments':comments}}
    
    _id = collection.update_one(filter, newvalues)
    return json.dumps({'_id':ObjectId(id)})
	
#Select all users
#@app.route('/all', methods=['POST'])
@app.route('/all', methods=['GET'])
def all():
    collection = db["customers"]
    customers = list(collection.find())
    print(customers)
    #convert _id to str
    return json.dumps(customers, default=str)
	
@app.route("/image", methods=["POST"])
def image_save():
    collection = db["image"]
    content = request.get_json()
    _id = collection.insert_one(content)
    return json.dumps({'id' : str(_id.inserted_id)})
    
@app.route('/images', methods=['GET'])
def image_list():
    collection = db["image"]
    images = list(collection.find())
    # convert _id to str
    print(f"Got {len(images)} images.")
    return json.dumps(images, default=str)
    
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)