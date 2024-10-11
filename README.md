# ARCHIVED

This project is no longer maintained and will not receive any further updates. If you plan to continue using it, please be aware that future security issues will not be addressed.

# Fotoquest DAO Mappings for 52n DAO SPI module

## Description

**Provide database customizations to provide Fotoquest data via 52n Series REST API.**

_This module provides custom Hibernate mappings and database views. It enables the [52n DAO SPI module](https://github.com/52North/dao-series-api) 
to connect to a Fotoquest database. The Web application provides then RESTful data access via 52n Series REST API._ 

The Fotoquest module provides customizations on the default behaviour of accessing 52n series data model. Accessing
Fotoquest data is therefore technically transparent for the DAO module which serves as backend access layer 
to retrieve data. The well defined RESTful Web interface makes it easy for lightweight clients to query and work with
the data. Besides pure data access, the data can be preprocessed with common IO functionalities e.g. 
  * prerendering of series data, 
  * generalization, 
  * overlaying of data from multiple series
  * conversion of raw data to other formats like pdf and png

The following main frameworks are used to provide this API:
- [Hibernate](https://hibernate.org/) 
- [52°North DAO SPI Impl](https://github.com/52North/dao-series-api/)
- [52°North Series API](https://github.com/52North/series-rest-api/) 

## Build/Install

### Preparation
1. Get Series REST API: `$ git clone https://github.com/52North/series-rest-api`
1. Install [Git](https://git-scm.org) and [Maven](https://maven.apache.org)
   and make it accessable from `$PATH`

### Database
1. If required, setup the Fotoquest Database (postgresql + postgis + fotoquest dump)
1. Create views in the database by executing
`fotoquest-mappings/src/extension/fotoquest/update_views.sh`
(adjust user with sufficient rights beforehand)

### Build Web Application
1. `$ git clone https://github.com/52North/fotoquest-series-api`
1. `$ cd fotoquest-rest-api`
1. `$ cp fotoquest-webapp/src/main/resources/application.properties <custom_dir>/fotoquest.properties`
1. `mvn clean install -Dlocal.configFile=file:/<custom_dir>/fotoquest.properties -Denv={dev,ci,prod}`
(use either of these to set different log levels)
1. Change database connection settings in `<custom_dir>/fotoquest.properties`
1. Deploy `fotoquest-webapp/target/fotoquest-webapp.war` to
`${CATALINA_HOME}/webapps/` folder
1. Start Tomcat and access `http://<server>:<port>/fotoquest-webapp/api/v1/`


### Further Lookups
* https://github.com/52North/series-rest-api
* https://github.com/52North/dao-series-api
* https://52north.github.io/series-rest-api/


## License

The module is published under the [GNU General Public License v2 (GPLv2)](http://www.gnu.org/licenses/gpl-2.0.html).

## Changelog
- https://github.com/52North/fotoquest-series-api/blob/develop/CHANGELOG.md
- for detailed infos check https://github.com/52North/fotoquest-series-api/pulls?q=is%3Apr+is%3Aclosed

## Contributing
We try to follow [the GitFlow model](http://nvie.com/posts/a-successful-git-branching-model/), 
although we do not see it that strict. 

However, make sure to do pull requests for features, hotfixes, etc. by
making use of GitFlow. Altlassian provides [a good overview]
(https://www.atlassian.com/de/git/workflows#!workflow-gitflow). of the 
most common workflows.

## Contact
Henning Bredel (h.bredel@52north.org)
