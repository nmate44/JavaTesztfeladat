# JavaTesztfeladat
Task for testing Java developer skills. Java 8 CL App w/ Maven and Postgresql

## The application:
- Syncs listing data (1000 rows per run) from a REST API
- Validates where necessary both through a coded Validator class and SQL constraints
- Store the valid data in a local PostgreSQL database (SQL statements for tables included)
- Generates a CSV log file from all the invalid fields
- Generates a JSON report file according to the demands and stores it to a local FTP Server (I used FileZilla)

Other dependencies: Unirest-Java, Apache Commons Net and Validator
