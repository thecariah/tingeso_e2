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
-- Name: historial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historial (
    id bigint,
    fecha_ingreso character varying,
    hora_ingreso character varying,
    monto_reparaciones integer,
    monto_recargos integer,
    monto_descuentos integer,
    monto_iva integer,
    costo_total integer,
    fecha_salida character varying,
    hora_salida character varying,
    fecha_retiro character varying,
    hora_retiro character varying,
    patente character varying,
    bono integer,
    kilometraje integer
);


ALTER TABLE public.historial OWNER TO postgres;

--
-- Data for Name: historial; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historial (id, fecha_ingreso, hora_ingreso, monto_reparaciones, monto_recargos, monto_descuentos, monto_iva, costo_total, fecha_salida, hora_salida, fecha_retiro, hora_retiro, patente, bono, kilometraje) FROM stdin;
\.


--
-- PostgreSQL database dump complete
--

