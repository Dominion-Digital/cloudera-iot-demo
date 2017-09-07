#!/usr/bin/env bash
#*******************************************************************************
# Copyright (c) 2017 Red Hat, Inc and others
#
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#     Red Hat, Inc. - initial API and implementation
#
#******************************************************************************

set -e

. common.sh

OPENSHIFT_PROJECT_NAME=${OPENSHIFT_PROJECT_NAME:=redhat-iot}
IMAGE_VERSION=${IMAGE_VERSION:=2017-04-08}

# print error and exit when necessary

die() { printf "$@" "\n" 1>&2 ; exit 1; }

# test if the project is already created ... fail otherwise 

$OC describe "project/$OPENSHIFT_PROJECT_NAME" &>/dev/null || die "Project '$OPENSHIFT_PROJECT_NAME' not created or OpenShift is unreachable. Try with:\n\n\toc new-project eclipse-kapua\n\n"

### Create Kapua from template

echo Creating Kapua from template ...
$OC new-app -n "$OPENSHIFT_PROJECT_NAME" -f cloudera-iot-demo-kapua.yml -p IMAGE_VERSION=${IMAGE_VERSION}
echo Creating Kapua from template ... done!
