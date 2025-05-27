CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE registro_popular (
    fid BIGINT PRIMARY KEY,       
    DATA DATE,                     
    CATEGORIA BIGINT DEFAULT 1,      
    STATUS BIGINT DEFAULT 10,         
    OBS TEXT,                       
    image TEXT,                     
    geometry geometry,              
    RUA TEXT,                       
    BAIRRO TEXT                     

)