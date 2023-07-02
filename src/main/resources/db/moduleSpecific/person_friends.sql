-- liquibase formatted sql
-- changeset liquibase:1
CREATE TABLE IF NOT EXISTS public."person_friends" (
    "id" UUID NOT NULL DEFAULT gen_random_uuid(),
    "person_id" UUID NOT NULL ,
    "friend_id" UUID NOT NULL ,
    CONSTRAINT "user_friends_PK" PRIMARY KEY ("id"),
    CONSTRAINT "user_id_FK" FOREIGN KEY ("person_id")
        REFERENCES public."person" ("id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "friend_id_FK" FOREIGN KEY ("friend_id")
        REFERENCES public."person" ("id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    UNIQUE (person_id, friend_id)

);