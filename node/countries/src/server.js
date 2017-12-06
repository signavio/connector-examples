const _ = require("lodash");
const express = require("express");
const app = express();

const descriptor = require("./descriptor");
const de = require("cldr-localenames-modern/main/de/territories");
const en = require("cldr-localenames-modern/main/en/territories");
const es = require("cldr-localenames-modern/main/es/territories");
const fr = require("cldr-localenames-modern/main/fr/territories");
const data = _.merge({}, de, en, es, fr);

// Serves the JSON type descriptor.
app.get("/", function(req, res) {
    res.json(descriptor);
});

// Serves CLDR countries, with ISO 3166-1 alpha-2 country codes and country names in the given language.
app.get("/:language/options", function(request, response) {
  const language = request.params.language;

  if(!data.main[language]) {
      return response.status(404).send();
  }

  const territories = data.main[language].localeDisplayNames.territories;

  const countryCodes = _.filter(_.keys(territories), function(code) {
    // Exclude continents, alternative names and ‘Unknown region’ (ZZ).
    return code.length == 2 && code != "ZZ";
  });

  const options = _.map(countryCodes, function(code) {
      return {
        code: code,
        name: territories[code]
      };
  });

  const query = request.query.filter || "";

  const filteredOptions = _.filter(options, function(option) {
      return _.includes(option.name.toLowerCase(), query.toLowerCase());
  });

  response.json(filteredOptions);
});

app.listen(5000, function() {
    console.log("Connector listening on port 5000.");
});
