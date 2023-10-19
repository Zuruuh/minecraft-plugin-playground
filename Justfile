deps:
    cd spigot && java -jar ./BuildTools.jar
    fd --no-ignore-vcs --glob -d 1 'spigot*.jar' spigot -x cp {} ./server/spigot.jar
    gradle dependencies

build:
    gradle jar

install: build
    cp build/libs/minecraft-plugin-playground-*.jar ./server/plugins/

start: install
    cd server && java -Xms2G -Xmx4G -jar ./spigot.jar nogui
