package main

// Signavio Workflow connector example that provides a data source for a list of customers.

import (
	"encoding/json"
	"net/http"
	"os"
	"time"
	"strings"
)

var address = ":9000"

type Customer struct {
	Id               string `json:"id"`
	FullName         string `json:"fullName"`
	Email            string `json:"email"`
	SubscriptionType string `json:"subscriptionType"`
	Discount         int `json:"discount"`
	Since            time.Time `json:"since"`
}

var customers []Customer

// Loads customer data from a JSON file.
func load() {
	dataFile, err := os.Open("data.json")
	if err != nil {
		panic(err)
	}
	defer dataFile.Close()
	var dataFileDecoder *json.Decoder = json.NewDecoder(dataFile)
	if err != nil {
		panic(err)
	}
	err = dataFileDecoder.Decode(&customers)
	if err != nil {
		panic(err)
	}
}

// Finds a customer by ID.
func findOne(id string) (Customer, bool) {
	for _, customer := range customers {
		if customer.Id == id {
			return customer, true
		}
	}
	return Customer{}, false
}

// Serves a single customer.
func customer(response http.ResponseWriter, request *http.Request) {
	id := strings.TrimPrefix(request.URL.Path, "/customers/")
	customer, found := findOne(id)
	if found {
		json, _ := json.MarshalIndent(customer, "", "  ")
		response.Header().Set("Content-Type", "application/json")
		response.Write(json)
	} else {
		response.WriteHeader(404) // Not Found
	}
}

// Finds customers whose names contain the given query.
func find(query string) []Customer {
	results := make([]Customer, 0)
	for _, customer := range customers {
		if strings.Contains(strings.ToLower(customer.FullName), strings.ToLower(query)) {
			results = append(results, customer)
		}
	}
	return results
}

// Serves the list of customers.
func options(response http.ResponseWriter, request *http.Request) {
	query := request.URL.Query().Get("filter")
	var filteredCustomers []Customer
	if (query == "") {
		filteredCustomers = customers
	} else {
		filteredCustomers = find(query)
	}
	json, _ := json.MarshalIndent(&filteredCustomers, "", "  ")
	response.Header().Set("Content-Type", "application/json")
	response.Write(json)
}

func descriptor(response http.ResponseWriter, request *http.Request)  {
	http.ServeFile(response, request, "descriptor.json")
}

// Serves a connector over HTTP.
func main() {
	load();
	http.HandleFunc("/", descriptor)
	http.HandleFunc("/customer/options", options)
	http.HandleFunc("/customer/", customer)
	println("Listening on " + address)
	panic(http.ListenAndServe(address, nil))
}
