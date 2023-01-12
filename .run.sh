###########################################################################
# Run-script that can be used by Visual Code Code Runner that runs code
# faster from compiled class files without packaging.
#

# source .env.sh to set CLASSPATH variable, if not already set
if [ -z "${CLASSPATH}" ]; then
    source .env.sh
fi

java -cp ${CLASSPATH} de.freerider.application.FreeriderApplication
