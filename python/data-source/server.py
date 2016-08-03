from flask import Flask, abort, request

import json

app = Flask(__name__)

descriptor = None
data = None

with open("./descriptor.json") as f:
    descriptor = json.loads(f.read())

with open("./data.json") as f:
    data = json.loads(f.read())

@app.route("/")
def get_descriptor():
    return json.dumps(descriptor)

@app.route("/<kind>/options")
def get_options(kind):
    if kind not in data:
        abort(404)

        return

    options = [ { "id": v["id"], "name": v["fullName"] } for (k, v) in data[kind].iteritems() ]

    query = request.args.get("filter", "").lower()

    if query:
        options = filter(lambda option: query in option["name"].lower(), options)

    return json.dumps(options)

@app.route("/<kind>/<id>")
def get_entry(kind, id):
    if kind not in data:
        abort(404)

        return

    options = data[kind]

    if id not in options:
        abort(404)

        return

    entry = options[id]

    return json.dumps(entry)


if __name__ == "__main__":
    app.run()
