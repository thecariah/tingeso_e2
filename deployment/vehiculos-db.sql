--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

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
-- Name: vehiculos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehiculos (
    patente character varying,
    marca character varying,
    modelo character varying,
    tipo character varying,
    fabricacion integer,
    motor character varying,
    asientos integer
);


ALTER TABLE public.vehiculos OWNER TO postgres;

--
-- Data for Name: vehiculos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vehiculos (patente, marca, modelo, tipo, fabricacion, motor, asientos) FROM stdin;
\.


--
-- PostgreSQL database dump complete
--

