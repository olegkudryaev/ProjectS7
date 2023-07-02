-- liquibase formatted sql
-- changeset liquibase:1
CREATE TABLE IF NOT EXISTS public."person"
(
    "id" uuid NOT NULL,
    "username" varchar(20) NOT NULL,
    "password" varchar(20) NOT NULL,
    "role" varchar(32) NOT NULL,
    CONSTRAINT "person_PK" PRIMARY KEY ("id")
);

-- changeset liquibase:2
INSERT INTO public."person"("id", "username", "password", "role")
VALUES ('7cb4d732-6f69-41b6-a649-4824992df26b', 'Admin', 123, 'ADMIN');