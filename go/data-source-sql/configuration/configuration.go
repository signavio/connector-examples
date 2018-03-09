package configuration

// JSON-based connector configuration

import (
	"encoding/json"
  "io/ioutil"
  "log"
)

type Configuration struct {
  DatabaseDriver string `json:"database_driver"`
  DatabaseUrl string `json:"database_url"`
}

func Load(path string) Configuration {
	file, err := ioutil.ReadFile(path)
	if err != nil {
		log.Fatal("Configuration file not found", err)
	}

	var configuration Configuration
	err = json.Unmarshal(file, &configuration)
	if err != nil {
		log.Fatal("Configuration syntax error", err)
	}

	return configuration
}
