const _ = require("lodash");
const express = require("express");
const app = express();

const descriptor = require("./descriptor");
const data = require("./data");

app.get("/", function (req, res) {
    res.json(descriptor);
});

app.get("/:type/options", function (req, res) {
    const type = req.params.type;

    if (!data[type]) {
        return res.status(404).send();
    }

    const query = req.query.filter || "";

    const options = _.filter(data[type], function (value) {
        return _.includes(value.fullName.toLowerCase(), query.toLowerCase());
    });

    res.json(_.map(options, function (value) {
        return {
            id: value.id,
            name: value.fullName,
        };
    }));
});

app.get("/:type/options/:id", function (req, res) {
    const type = req.params.type
    const id = req.params.type

    if (!data[type]) {
        return res.status(404).send();
    }

    const resource = data[type][id];

    if (!resource) {
        return res.status(404).send();
    }

    res.json({
        id: resource.id,
        name: resource.fullName,
    });
})

app.get("/:type/:id", function (req, res) {
    const type = req.params.type;
    const id = req.params.id;

    if (!data[type]) {
        return res.status(404).send();
    }

    const resource = data[type][id];

    if (!resource) {
        return res.status(404).send();
    }

    res.json(resource);
});

app.listen(5000, function () {
    console.log("Data source listening on port 5000.");
});
