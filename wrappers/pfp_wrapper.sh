#!/bin/ksh

export JAVA_1_8_HOME=/usr/java8_64/bin

function pfp { 
	set -f
	typeset params="$*"
	"${JAVA_1_8_HOME}/java" -cp ".:./pipe_file_parser.jar:./ap_batches.jar" blog.javamagic.pfp.PfpRunner "${params}"
	typeset ret_code=$?
	set +f
	return ${ret_code}
}