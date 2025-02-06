# import main Flask class and request object
from flask import Flask, request

# create the Flask app
app = Flask(__name__)

@app.route('/login')
def login():
    request_data = request.get_json()
    username = request_data['username']
    password = request_data['password']
    #do something to validate
    return '{"authorized"=true}'

@app.route('/query-example')
def query_example():
    # if key doesn't exist, returns None
    language = request.args.get('language')

    # if key doesn't exist, returns a 400, bad request error
    framework = request.args['framework']

    # if key doesn't exist, returns None
    website = request.args.get('website')

    return '''
              <h1>The language value is: {}</h1>
              <h1>The framework value is: {}</h1>
              <h1>The website value is: {}'''.format(language, framework, website)

 # allow both GET and POST requests
@app.route('/form-example', methods=['GET', 'POST'])
def form_example():
    # handle the POST request
    if request.method == 'POST':
        language = request.form.get('language')
        framework = request.form.get('framework')
        return 'POST'
    # otherwise handle the GET request
    return ' GET'

@app.route('/json-example')
def json_example():
    return 'JSON Object Example'

if __name__ == '__main__':
    # run app in debug mode on port 5000
    app.run('0.0.0.0', debug=True, port=5000)

    {
        "language" : "Python",
        "framework" : "Flask",
        "website" : "Scotch",
        "version_info" : {
            "python" : "3.9.0",
            "flask" : "1.1.2"
        },
        "examples" : ["query", "form", "json"],
        "boolean_test" : True
    }