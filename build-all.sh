for folder in $(ls -d *service/);
do (
  cd "$folder" || continue
  ./mvnw package  1> /dev/null || continue
  cd target || continue
  mv *.jar build.jar || continue
  cd ..
  cd ..
) done