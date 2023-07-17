# 최초 1회 실행
mkdir -p ./infra/mysql/data
mkdir -p ./infra/mysql/conf/sql
sudo chmod -R 777 ./infra/mysql
sudo chmod -R 755 ./infra/mysql/conf/*.cnf

docker-compose up -d

echo "sleep 20s"
sleep 20

# db 생성
docker cp ./infra/mysql/conf/sql/init.sql mysql-moyeomoyeo:/tmp/init.sql
docker exec -it mysql-moyeomoyeo mysql -uroot -proot -Dmysql -e "source /tmp/init.sql"

# db 생성 및 user 확인
docker exec -it mysql-moyeomoyeo mysql -uroot -proot -Dmysql -e "show databases"
docker exec -it mysql-moyeomoyeo mysql -uroot -proot -Dmysql -e "select user, host from user"