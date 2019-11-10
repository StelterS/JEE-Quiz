#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

WILDFLY_HOME=.../wildfly-17.0.1.Final

${WILDFLY_HOME}/bin/jboss-cli.sh --connect --file=${DIR}/database_connection.cli
${WILDFLY_HOME}/bin/jboss-cli.sh --connect --file=${DIR}/security_domain.cli
${WILDFLY_HOME}/bin/jboss-cli.sh --connect --file=${DIR}/auth_method.cli
