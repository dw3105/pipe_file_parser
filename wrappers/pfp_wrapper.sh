#!/bin/ksh

export JAVA_1_8_HOME=/usr/java8_64/bin
export PFP_HOME=/home/pfp

function pfp { 
	set -f
	typeset params="$*"
	"${JAVA_1_8_HOME}/java" -cp "${PFP_HOME}:${PFP_HOME}/pipe_file_parser.jar" blog.javamagic.pfp.PfpRunner "${params}"
	set +f
}