module.exports = {
  key: "countries",
  name: "Countries",
  description: "List of country names, in different languages",
  typeDescriptors: [
    {
      "key" : "de",
      "name" : "Land",
      "fields" : [
        {
          "key" : "code",
          "name" : "Ländercode",
          "type" : {
            "name" : "text"
          }
        },
        {
          "key" : "name",
          "name" : "Name",
          "type" : {
            "name" : "text"
          }
        }
      ],
      "optionsAvailable" : true,
      "fetchOneAvailable" : false
    },
    {
      "key" : "en",
      "name" : "Country",
      "fields" : [
        {
          "key" : "code",
          "name" : "Code",
          "type" : {
            "name" : "text"
          }
        },
        {
          "key" : "name",
          "name" : "Name",
          "type" : {
            "name" : "text"
          }
        }
      ],
      "optionsAvailable" : true,
      "fetchOneAvailable" : false
    },
    {
      "key" : "es",
      "name" : "País",
      "fields" : [
        {
          "key" : "code",
          "name" : "Código",
          "type" : {
            "name" : "text"
          }
        },
        {
          "key" : "name",
          "name" : "Nombre",
          "type" : {
            "name" : "text"
          }
        }
      ],
      "optionsAvailable" : true,
      "fetchOneAvailable" : false
    },
    {
      "key" : "fr",
      "name" : "Pays",
      "fields" : [
        {
          "key" : "code",
          "name" : "Code",
          "type" : {
            "name" : "text"
          }
        },
        {
          "key" : "name",
          "name" : "Nom",
          "type" : {
            "name" : "text"
          }
        }
      ],
      "optionsAvailable" : true,
      "fetchOneAvailable" : false
    }
  ],
  version: 1,
  protocolVersion: 1
}
