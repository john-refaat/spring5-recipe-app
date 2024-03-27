-- Use to run mysl db docker image
-- docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v ~/docker/data/mysql:/var/lib/mysql -d mysql

-- create databases
CREATE DATABASE IF NOT EXISTS sfg_dev;
CREATE DATABASE IF NOT EXISTS sfg_prod;

-- Create service accounts
CREATE USER IF NOT EXISTS 'sfg_dev_u'@'localhost' IDENTIFIED BY 'sfg_dev_u';
CREATE USER IF NOT EXISTS 'sfg_prod_u'@'localhost' IDENTIFIED BY 'sfg_prod_u';
CREATE USER IF NOT EXISTS 'sfg_dev_u'@'%' IDENTIFIED BY 'sfg_dev_u';
CREATE USER IF NOT EXISTS 'sfg_prod_u'@'%' IDENTIFIED BY 'sfg_prod_u';

-- Grants
GRANT SELECT ON sfg_dev.* to 'sfg_dev_u'@'localhost';
GRANT INSERT ON sfg_dev.* to 'sfg_dev_u'@'localhost';
GRANT UPDATE ON sfg_dev.* to 'sfg_dev_u'@'localhost';
GRANT DELETE ON sfg_dev.* to 'sfg_dev_u'@'localhost';
GRANT SELECT ON sfg_prod.* to 'sfg_prod_u'@'localhost';
GRANT INSERT ON sfg_prod.* to 'sfg_prod_u'@'localhost';
GRANT UPDATE ON sfg_prod.* to 'sfg_prod_u'@'localhost';
GRANT DELETE ON sfg_prod.* to 'sfg_prod_u'@'localhost';
GRANT SELECT ON sfg_dev.* to 'sfg_dev_u'@'%';
GRANT INSERT ON sfg_dev.* to 'sfg_dev_u'@'%';
GRANT UPDATE ON sfg_dev.* to 'sfg_dev_u'@'%';
GRANT DELETE ON sfg_dev.* to 'sfg_dev_u'@'%';
GRANT SELECT ON sfg_prod.* to 'sfg_prod_u'@'%';
GRANT INSERT ON sfg_prod.* to 'sfg_prod_u'@'%';
GRANT UPDATE ON sfg_prod.* to 'sfg_prod_u'@'%';
GRANT DELETE ON sfg_prod.* to 'sfg_prod_u'@'%';