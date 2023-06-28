--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

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

--
-- Name: petrov; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA petrov;


ALTER SCHEMA petrov OWNER TO postgres;

--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cart; Type: TABLE; Schema: petrov; Owner: postgres
--

CREATE TABLE petrov.cart (
    id integer NOT NULL,
    promocode character varying(255)
);


ALTER TABLE petrov.cart OWNER TO postgres;

--
-- Name: cart_id_seq; Type: SEQUENCE; Schema: petrov; Owner: postgres
--

ALTER TABLE petrov.cart ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME petrov.cart_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: client; Type: TABLE; Schema: petrov; Owner: postgres
--

CREATE TABLE petrov.client (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    cart_id integer NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE petrov.client OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE; Schema: petrov; Owner: postgres
--

ALTER TABLE petrov.client ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME petrov.client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: product; Type: TABLE; Schema: petrov; Owner: postgres
--

CREATE TABLE petrov.product (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    price numeric NOT NULL
);


ALTER TABLE petrov.product OWNER TO postgres;

--
-- Name: product_client; Type: TABLE; Schema: petrov; Owner: postgres
--

CREATE TABLE petrov.product_client (
    id integer NOT NULL,
    id_product integer NOT NULL,
    id_cart integer NOT NULL,
    count integer NOT NULL
);


ALTER TABLE petrov.product_client OWNER TO postgres;

--
-- Name: product_client_id_seq; Type: SEQUENCE; Schema: petrov; Owner: postgres
--

ALTER TABLE petrov.product_client ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME petrov.product_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: product_id_seq; Type: SEQUENCE; Schema: petrov; Owner: postgres
--

ALTER TABLE petrov.product ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME petrov.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: cart; Type: TABLE DATA; Schema: petrov; Owner: postgres
--

COPY petrov.cart (id, promocode) FROM stdin;
1	
6	
7	
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: petrov; Owner: postgres
--

COPY petrov.client (id, name, username, password, cart_id, email) FROM stdin;
1	Misha	misha	3t45qfg	1	f423q3@wf.w
6	Misha	misha	3t45qfg	6	f423q3@wf.w
7	Misha	misha	3t45qfg	7	f423q3@wf.w
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: petrov; Owner: postgres
--

COPY petrov.product (id, name, price) FROM stdin;
2	Морковь	70
3	Морковь	170
6	Морковь	11
7	Морковь	12
8	Морковь	13
9	Морковь	999999
\.


--
-- Data for Name: product_client; Type: TABLE DATA; Schema: petrov; Owner: postgres
--

COPY petrov.product_client (id, id_product, id_cart, count) FROM stdin;
5	7	6	4
6	7	6	4
7	7	6	4
8	9	7	5
\.


--
-- Name: cart_id_seq; Type: SEQUENCE SET; Schema: petrov; Owner: postgres
--

SELECT pg_catalog.setval('petrov.cart_id_seq', 8, true);


--
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: petrov; Owner: postgres
--

SELECT pg_catalog.setval('petrov.client_id_seq', 8, true);


--
-- Name: product_client_id_seq; Type: SEQUENCE SET; Schema: petrov; Owner: postgres
--

SELECT pg_catalog.setval('petrov.product_client_id_seq', 9, true);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: petrov; Owner: postgres
--

SELECT pg_catalog.setval('petrov.product_id_seq', 10, true);


--
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: petrov; Owner: postgres
--

ALTER TABLE ONLY petrov.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- Name: product_client product_client_pkey; Type: CONSTRAINT; Schema: petrov; Owner: postgres
--

ALTER TABLE ONLY petrov.product_client
    ADD CONSTRAINT product_client_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: petrov; Owner: postgres
--

ALTER TABLE ONLY petrov.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: client client_cart_id_fk; Type: FK CONSTRAINT; Schema: petrov; Owner: postgres
--

ALTER TABLE ONLY petrov.client
    ADD CONSTRAINT client_cart_id_fk FOREIGN KEY (cart_id) REFERENCES petrov.cart(id);


--
-- Name: product_client product_client_cart_id_fk; Type: FK CONSTRAINT; Schema: petrov; Owner: postgres
--

ALTER TABLE ONLY petrov.product_client
    ADD CONSTRAINT product_client_cart_id_fk FOREIGN KEY (id_cart) REFERENCES petrov.cart(id);


--
-- Name: product_client product_client_products_id_fk; Type: FK CONSTRAINT; Schema: petrov; Owner: postgres
--

ALTER TABLE ONLY petrov.product_client
    ADD CONSTRAINT product_client_products_id_fk FOREIGN KEY (id_product) REFERENCES petrov.product(id);


--
-- PostgreSQL database dump complete
--

