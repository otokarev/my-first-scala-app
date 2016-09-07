# Subscriber schema

# --- !Ups

CREATE TABLE "subscriber" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "channel" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL,
    "actor_class" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);
INSERT INTO "channel"("title", "actor_class") VALUES ('Default', 'actors.workers.DummyActor');

CREATE TABLE "channel_subscriber" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL,
    "channel_id" varchar(255) NOT NULL,
    "subscriber_id" bigint(20),
    "cfg" TEXT,
    PRIMARY KEY ("id")
);
# --- !Downs

DROP TABLE "subscriber";
DROP TABLE "channel";
DROP TABLE "channel_subscriber";
