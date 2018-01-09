package main

import (
	"fmt"
	"log"
	"database/sql"
	"strings"
	_ "github.com/go-sql-driver/mysql"
)

var databaseDriver = "mysql"
var databaseUrl = "cldr:cldr@tcp(127.0.0.1:3306)/cldr"

type Countries struct {
  db *sql.DB
}

// Connect to the database.
func NewCountries() (*Countries) {
  countries := Countries{}
  countries.db = Database()
  return &countries
}

// Connects to the database and tests the connection
func Database() *sql.DB {
	db, err := sql.Open(databaseDriver, databaseUrl)
	if err != nil {
		log.Fatal(err.Error())
	}
	if err = db.Ping(); err != nil {
		log.Fatal(err.Error())
	}
	log.Print("Connected to database")
	return db
}

// Finds a country by code.
func (c Countries) FindOne(code string) (Option, bool) {
	var country Option
	if c.db == nil {
	  log.Fatal("No database")
	}
	err := c.db.QueryRow("select code, name from countries where code = ?", code).Scan(&country.Code, &country.Name)
	if err != nil {
		log.Print(err)
		return Option{}, false
	}
	return country, true
}

// Finds countries whose names contain the given query.
func (c Countries) Find(query string) []Option {
	sql := "select code, name from countries where '' = ? || lower(name) like ? order by name"
	results, err := c.db.Query(sql, query, fmt.Sprintf("%%%v%%", strings.ToLower(query)))
	if err != nil {
		panic(err.Error())
	}
	defer results.Close()

	var countries []Option
	for results.Next() {
		var country Option
		err = results.Scan(&country.Code, &country.Name)
		if err != nil {
			panic(err.Error())
		}
		countries = append(countries, country)
	}

	return countries
}
