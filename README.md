Dominion IoT Industry 4.0 Demo
==================================================
This is an example IoT demo showing a realtime updating dashboard of data streaming from an
IoT gateway device (based on Eclipse Kura) through an Eclipse Kapua-based instance.

Technologies used:

- [Eclipse Kapua](http://www.eclipse.org/kapua/)
- [AngularJS](http://angularjs.org)
- [Patternfly](http://patternfly.org)
- [JBoss Middleware](https://www.redhat.com/en/technologies/jboss-middleware) (EAP, JDG, and more to come)

Running on OpenShift
--------------------

The demo deploys as an Angular.js app running on a Node.js runtime, along with JBoss Data Grid and a Data Grid
proxy component that properly handles browser-based REST requests and relays to JBoss Data Grid via the Hotrod
protocol.

Eclipse Kapua is also deployed and acts as the IoT cloud management layer.

Follow these steps to build and run the demo:

1. Install and have access to an [OpenShift Container Platform](https://www.openshift.com/container-platform/) 3.4 or later or [OpenShift Origin](https://www.openshift.org/) 1.4 or later. You must be able to use the `oc` command line tool.

2. Clone this repo
```
git clone https://github.com/Dominion-Digital/cloudera-iot-demo
cd cloudera-iot-demo/openshift
```

3. Issue the following commands to create a new OpenShift project and deploy the demo components:
```
oc new-project cloudera-iot-demo --display-name="Cloudera IoT Demo"
oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default -n $(oc project -q)
./deploy.sh
```

If you see some components with "No Deployments" or are not building, you may need to add imagestream
definitions for ``wildfly`` and ``jboss-datagrid``. To do so, run these commands:

```
oc login -u system:admin (Or login with any userid that has cluster-admin privileges, TODO: Explain all options here)
oc create -n openshift -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/jboss-image-streams.json
oc create -n openshift -f https://raw.githubusercontent.com/openshift/origin/master/examples/image-streams/image-streams-centos7.json
```

Once everything is up and running, you can access the demo using the URL of the `dashboard` route,
for example `http://dashboard-cloudera-iot-demo.domain`

Confirm that all the components are running successfully:

```
oc get pods --show-all=false
```
You should see the following pods and their status:

|NAME                 |   READY     | STATUS  |
|---------------------|:-----------:|:-------:|
|dashboard-1-xxx      |    1/1      | Running |
|datastore-1-xxx      |    1/1      | Running |
|dashboard-proxy-1-xxx|    1/1      | Running |
|elasticsearch-1-xxx  |    1/1      | Running |
|kapua-api-1-wc1l7    |    1/1      | Running |
|kapua-broker-1-xxx   |    1/1      | Running |
|kapua-console-1-xxx  |    1/1      | Running |
|sql-1-xxx            |    1/1      | Running |
