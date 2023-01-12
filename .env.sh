###########################################################################
# Project-specific environment. Source with: source .env.sh
#

function build_classpath() {
    case "$(uname -o)" in
    "Cygwin")   sep=";" ;;      # use ";" as classpath separator for Windows, Cygwin
    "Msys")     sep=";" ;;      # and GitBash
    *)          sep=":" ;;      # use ":" for other OS: Mac, Linux, Unix, ...
    esac
    export CLASSPATH="target/classes"
    export CLASSPATH="${CLASSPATH}${sep}$(mvn dependency:build-classpath | grep repository)"
}

function show_cp() {
    echo ${CLASSPATH} | tr '[;:]' '\n' | \
        sed "/^C$/d"    # delete C: remains from path
                        # | sed "s/.*\.m2.repository.//"
}

build_classpath
echo "project environment sourced, \${CLASSPATH} rebuilt"

