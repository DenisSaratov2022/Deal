CREATE TABLE IF NOT EXISTS public.loan_offer
(
    id bigint NOT NULL,
    application_id bigint,
    is_insurance_enabled boolean NOT NULL,
    is_salary_client boolean NOT NULL,
    monthly_payment numeric(19,2),
    rate numeric(19,2),
    requested_amount numeric(19,2),
    term integer,
    total_amount numeric(19,2),
    CONSTRAINT loan_offer_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.passport
(
    id bigint NOT NULL,
    passport_issue_branch character varying(255),
    passport_issue_date date,
    passport_number character varying(6),
    passport_series character varying(4),
    CONSTRAINT passport_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.employment
(
    id bigint NOT NULL,
    account character varying(255),
    employer character varying(255),
    employment_status integer,
    "position" integer,
    salary numeric(19,2),
    work_experience_current integer,
    work_experience_total integer,
    CONSTRAINT employment_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.client
(
    id bigint NOT NULL,
    birth_date date,
    dependent_amount integer,
    email character varying(255),
    first_name character varying(255),
    gender integer,
    last_name character varying(255),
    marital_status integer,
    middle_nme character varying(255),
    employment bigint,
    passport bigint,
    CONSTRAINT client_pkey PRIMARY KEY (id),
    CONSTRAINT passport_fk FOREIGN KEY (passport)
        REFERENCES public.passport (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT employment_fk FOREIGN KEY (employment)
        REFERENCES public.employment (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS public.credit
(
    id bigint NOT NULL,
    amount numeric(19,2),
    credit_status integer,
    is_insurance_enabled boolean,
    is_salary_client boolean,
    monthly_payment numeric(19,2),
    psk numeric(19,2),
    rate numeric(19,2),
    term integer,
    CONSTRAINT credit_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS application
(
    id bigint NOT NULL,
    creation_date date,
    ses_code character varying(255),
    sign_date date,
    status integer,
    applied_offer bigint,
    client bigint,
    credit bigint,
    CONSTRAINT application_pkey PRIMARY KEY (id),
    CONSTRAINT applied_offer_fk FOREIGN KEY (applied_offer)
        REFERENCES public.loan_offer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT client_fk FOREIGN KEY (client)
        REFERENCES public.client (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT credit_fk FOREIGN KEY (credit)
        REFERENCES public.credit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.application_status_history
(
    id bigint NOT NULL,
    change_type integer,
    status integer,
    "time" timestamp without time zone,
    application bigint,
    CONSTRAINT application_status_history_pkey PRIMARY KEY (id),
    CONSTRAINT application_fk FOREIGN KEY (application)
        REFERENCES public.application (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS public.payment_schedule_element
(
    id bigint NOT NULL,
    date date,
    debt_payment numeric(19,2),
    interest_payment numeric(19,2),
    "number" integer,
    remaining_debt numeric(19,2),
    total_payment numeric(19,2),
    credit bigint,
    CONSTRAINT payment_schedule_element_pkey PRIMARY KEY (id),
    CONSTRAINT credit_fk FOREIGN KEY (credit)
        REFERENCES public.credit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);