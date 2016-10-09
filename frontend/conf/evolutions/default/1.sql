# Subscriber schema

# --- !Ups

CREATE TABLE "subscriber" (
    "id" UUID NOT NULL,
    "title" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "channel" (
    "id" UUID NOT NULL,
    "title" varchar(255) NOT NULL UNIQUE,
    "actor_class" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff7df4c8cb111e6b6722f15697ed957', 'Default - 01', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff7fc348cb111e6ae061f609584c67b', 'Default - 02', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff8173c8cb111e6834f43a5f2800a57', 'Default - 03', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff834608cb111e690f3dfcf52ede85f', 'Default - 04', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff858008cb111e68792b74388869f47', 'Default - 05', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff88aaa8cb111e6938e77c056b22d5a', 'Default - 06', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff8be448cb111e699cfd380eff7124b', 'Default - 07', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff8f3be8cb111e684a33b24e470e250', 'Default - 08', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff926228cb111e6b03dab6ba2339195', 'Default - 09', 'actors.workers.DummyActor');
INSERT INTO "channel"("id", "title", "actor_class") VALUES ('6ff958048cb111e681cf47c13e4dc34f', 'Default - 10', 'actors.workers.DummyActor');

CREATE TABLE "channel_subscriber" (
    "id" UUID NOT NULL,
    "title" varchar(255) NOT NULL,
    "channel_id" UUID NOT NULL,
    "subscriber_id" UUID,
    "cfg" TEXT,
    PRIMARY KEY ("id")
);
# --- !Downs

DROP TABLE "subscriber";
DROP TABLE "channel";
DROP TABLE "channel_subscriber";
