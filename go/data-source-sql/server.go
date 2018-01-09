package main

// Signavio Workflow connector example that provides a data source for a list of countries.

import (
	"encoding/json"
	"net/http"
	"strings"
)

var address = ":9000"
var countries *Countries

// Option JSON type required by the connector API.
type Option struct {
	Code string `json:"id"`
	Name string `json:"name"`
}

// Serves a single country.
func country(response http.ResponseWriter, request *http.Request) {
	code := strings.TrimPrefix(request.URL.Path, "/country/")
	country, found := countries.FindOne(code)
	if found {
		json, _ := json.MarshalIndent(country, "", "  ")
		response.Header().Set("Content-Type", "application/json")
		response.Write(json)
	} else {
		response.WriteHeader(404) // Not Found
	}
}

// Serves the list of countries.
func ServeOptions(response http.ResponseWriter, request *http.Request) {
	query := request.URL.Query().Get("filter")
	var options = countries.Find(query)
	json, _ := json.MarshalIndent(&options, "", "  ")
	response.Header().Set("Content-Type", "application/json")
	response.Write(json)
}

// Serves a single country option.
func ServeOption(response http.ResponseWriter, request *http.Request) {
	code := strings.TrimPrefix(request.URL.Path, "/country/options/")
	country, found := countries.FindOne(code)
	if found {
		json, _ := json.MarshalIndent(country, "", "  ")
		response.Header().Set("Content-Type", "application/json")
		response.Write(json)
	} else {
		response.WriteHeader(404) // Not Found
	}
}

func Descriptor(response http.ResponseWriter, request *http.Request) {
	http.ServeFile(response, request, "descriptor.json")
}

// Serves a connector over HTTP.
func main() {
	countries = NewCountries()

	http.HandleFunc("/", Descriptor)
	http.HandleFunc("/country/options/", ServeOption)
	http.HandleFunc("/country/options", ServeOptions)
	println("Listening on " + address)
	panic(http.ListenAndServe(address, nil))
}
