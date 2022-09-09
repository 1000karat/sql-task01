docker-compose up -d
docker exec -it 9fcab8f03ee2 mysql -u app -ppass -D app -e "TRUNCATE auth_codes; TRUNCATE cards; TRUNCATE card_transactions; DELETE FROM users;"
java -jar artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass
rem docker-compose stop
rem ##https://stackoverflow.com/questions/26598738/how-to-create-user-database-in-script-for-docker-postgres
rem ##https://www.shellhacks.com/mysql-run-query-bash-script-linux-command-line/