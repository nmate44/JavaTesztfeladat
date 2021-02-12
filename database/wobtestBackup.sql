--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

-- Started on 2021-02-12 12:21:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16452)
-- Name: listing; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.listing (
    id uuid NOT NULL,
    title character varying(100) NOT NULL,
    description character varying(200) NOT NULL,
    inventory_item_location_id uuid NOT NULL,
    listing_price double precision NOT NULL,
    currency character varying(3) NOT NULL,
    quantity integer NOT NULL,
    listing_status smallint NOT NULL,
    marketplace smallint NOT NULL,
    upload_time date NOT NULL,
    owner_email_address character varying(100) NOT NULL
);


ALTER TABLE public.listing OWNER TO admin;

--
-- TOC entry 202 (class 1259 OID 16457)
-- Name: listingStatus; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public."listingStatus" (
    id smallint NOT NULL,
    status_name character varying(50) NOT NULL
);


ALTER TABLE public."listingStatus" OWNER TO admin;

--
-- TOC entry 200 (class 1259 OID 16443)
-- Name: location; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.location (
    id uuid NOT NULL,
    manager_name character varying(100) NOT NULL,
    phone character varying(20) NOT NULL,
    address_primary character varying(200) NOT NULL,
    address_secondary character varying(200),
    country character varying(100) NOT NULL,
    town character varying(100) NOT NULL,
    postal_code character varying(100) NOT NULL
);


ALTER TABLE public.location OWNER TO admin;

--
-- TOC entry 203 (class 1259 OID 16462)
-- Name: marketplace; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.marketplace (
    id smallint NOT NULL,
    marketplace_name character varying(100) NOT NULL
);


ALTER TABLE public.marketplace OWNER TO admin;

--
-- TOC entry 3003 (class 0 OID 16452)
-- Dependencies: 201
-- Data for Name: listing; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.listing (id, title, description, inventory_item_location_id, listing_price, currency, quantity, listing_status, marketplace, upload_time, owner_email_address) FROM stdin;
\.


--
-- TOC entry 3004 (class 0 OID 16457)
-- Dependencies: 202
-- Data for Name: listingStatus; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public."listingStatus" (id, status_name) FROM stdin;
\.


--
-- TOC entry 3002 (class 0 OID 16443)
-- Dependencies: 200
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.location (id, manager_name, phone, address_primary, address_secondary, country, town, postal_code) FROM stdin;
\.


--
-- TOC entry 3005 (class 0 OID 16462)
-- Dependencies: 203
-- Data for Name: marketplace; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.marketplace (id, marketplace_name) FROM stdin;
\.


--
-- TOC entry 2866 (class 2606 OID 16461)
-- Name: listingStatus listingStatus_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public."listingStatus"
    ADD CONSTRAINT "listingStatus_pkey" PRIMARY KEY (id);


--
-- TOC entry 2864 (class 2606 OID 16456)
-- Name: listing listing_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.listing
    ADD CONSTRAINT listing_pkey PRIMARY KEY (id);


--
-- TOC entry 2862 (class 2606 OID 16450)
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- TOC entry 2868 (class 2606 OID 16466)
-- Name: marketplace marketplace_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.marketplace
    ADD CONSTRAINT marketplace_pkey PRIMARY KEY (id);


--
-- TOC entry 2869 (class 2606 OID 16467)
-- Name: listing listing_inventory_item_location_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.listing
    ADD CONSTRAINT listing_inventory_item_location_id_fkey FOREIGN KEY (inventory_item_location_id) REFERENCES public.location(id) NOT VALID;


--
-- TOC entry 2870 (class 2606 OID 16472)
-- Name: listing listing_listing_status_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.listing
    ADD CONSTRAINT listing_listing_status_fkey FOREIGN KEY (listing_status) REFERENCES public."listingStatus"(id) NOT VALID;


--
-- TOC entry 2871 (class 2606 OID 16477)
-- Name: listing listing_marketplace_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.listing
    ADD CONSTRAINT listing_marketplace_fkey FOREIGN KEY (marketplace) REFERENCES public.marketplace(id) NOT VALID;


-- Completed on 2021-02-12 12:21:08

--
-- PostgreSQL database dump complete
--

