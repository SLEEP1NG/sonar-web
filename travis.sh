#!/bin/bash

set -euo pipefail

function installTravisTools {
  mkdir ~/.local
  curl -sSL https://github.com/SonarSource/travis-utils/tarball/v28 | tar zx --strip-components 1 -C ~/.local
  source ~/.local/bin/install
}

installTravisTools

case "$TEST" in

ci)
  regular_mvn_build_deploy_analyze
  ;;

*)
  echo "Unexpected TEST mode: $TEST"
  exit 1
  ;;

esac
