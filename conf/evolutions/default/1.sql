# Subscriber schema

# --- !Ups

CREATE TABLE "subscriber" (
    "id" bigint(20) NOT NULL AUTO_INCREMENT,
    "title" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

# --- !Downs

DROP TABLE "subscriber";