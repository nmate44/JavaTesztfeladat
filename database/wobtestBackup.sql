--
-- PostgreSQL Database Backup for WoB Test
--

-- Tables:

-- public.marketplace
CREATE TABLE public.marketplace
(
    id smallint NOT NULL,
    marketplace_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT marketplace_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;
-- End of public.marketplace

-- public.listing_status
CREATE TABLE public.listing_status
(
    id smallint NOT NULL,
    status_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "listingStatus_pkey" PRIMARY KEY (id)
)

    TABLESPACE pg_default;
-- End of public.listing_status

-- public.location
CREATE TABLE public.location
(
    id uuid NOT NULL,
    manager_name character varying(100) COLLATE pg_catalog."default",
    phone character varying(20) COLLATE pg_catalog."default",
    address_primary character varying(200) COLLATE pg_catalog."default",
    address_secondary character varying(200) COLLATE pg_catalog."default",
    country character varying(100) COLLATE pg_catalog."default",
    town character varying(100) COLLATE pg_catalog."default",
    postal_code character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT location_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;
-- End of public.location

-- public.listing
CREATE TABLE public.listing
(
    id uuid NOT NULL,
    title character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description character varying(200) COLLATE pg_catalog."default" NOT NULL,
    location_id uuid NOT NULL,
    listing_price numeric(20,2) NOT NULL,
    currency character varying(3) COLLATE pg_catalog."default" NOT NULL,
    quantity integer NOT NULL,
    listing_status smallint NOT NULL,
    marketplace smallint NOT NULL,
    upload_time date NOT NULL,
    owner_email_address character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT listing_pkey PRIMARY KEY (id),
    CONSTRAINT listing_inventory_item_location_id_fkey FOREIGN KEY (location_id)
        REFERENCES public.location (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT listing_listing_status_fkey FOREIGN KEY (listing_status)
        REFERENCES public.listing_status (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT listing_marketplace_fkey FOREIGN KEY (marketplace)
        REFERENCES public.marketplace (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT owner_email_address_check CHECK (owner_email_address::text ~~ '%_@__%.__%'::text) NOT VALID,
    CONSTRAINT listing_price_check CHECK (listing_price > 0::numeric) NOT VALID
)

    TABLESPACE pg_default;
-- End of public.listing