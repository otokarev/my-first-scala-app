# Subscriber schema

# --- !Ups

CREATE TABLE "subscriber" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "channel" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL UNIQUE,
    "actor_class" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 01', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 02', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 03', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 04', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 05', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 06', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 07', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 08', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 09', 'actors.workers.DummyActor');
INSERT INTO "channel"("title", "actor_class") VALUES ('Default - 10', 'actors.workers.DummyActor');

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
