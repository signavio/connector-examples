package countries

// Access to a database of country names.

import (
	"fmt"
	"log"
	"database/sql"
	"strings"
	_ "github.com/go-sql-driver/mysql"
	"github.com/signavio/connector-examples/go/data-source-sql/configuration"
)

var databaseDriver = "mysql"
var databaseUrl = "cldr:cldr@tcp(127.0.0.1:3306)/cldr"

type Countries struct {
  db *sql.DB
}

// JSON type required by the connector API.
type Country struct {
	Code string `json:"id"`
	Name string `json:"name"`
}

// Connect to the database.
func NewCountries(configuration configuration.Configuration) (*Countries) {
  countries := Countries{}
  countries.db = Database(configuration)
  return &countries
}

// Connects to the database and tests the connection
func Database(configuration configuration.Configuration) *sql.DB {
	db, err := sql.Open(configuration.DatabaseDriver, configuration.DatabaseUrl)
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
func (c Countries) FindOne(code string) (Country, bool) {
	var country Country
	if c.db == nil {
	  log.Fatal("No database")
	}
	err := c.db.QueryRow("select code, name from countries where code = ?", code).Scan(&country.Code, &country.Name)
	if err != nil {
		log.Print(err)
		return Country{}, false
	}
	return country, true
}

// Finds countries whose names contain the given query.
func (c Countries) Find(query string) []Country {
	sql := "select code, name from countries where '' = ? || lower(name) like ? order by name"
	results, err := c.db.Query(sql, query, fmt.Sprintf("%%%v%%", strings.ToLower(query)))
	if err != nil {
		panic(err.Error())
	}
	defer results.Close()

	var countries []Country
	for results.Next() {
		var country Country
		err = results.Scan(&country.Code, &country.Name)
		if err != nil {
			panic(err.Error())
		}
		countries = append(countries, country)
	}

	return countries
}
