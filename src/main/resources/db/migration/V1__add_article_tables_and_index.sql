CREATE TABLE IF NOT EXISTS article (
    url VARCHAR NOT NULL PRIMARY KEY,
    content TEXT NOT NULL,
    time_created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    time_modified TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS article_data (
    article_source VARCHAR NOT NULL,
    url VARCHAR NOT NULL,
    title_for_display VARCHAR NOT NULL,
    title_for_analysis VARCHAR NOT NULL,
    uploaded_instant TIMESTAMP WITH TIME ZONE NOT NULL,
    read_instant TIMESTAMP WITH TIME ZONE NOT NULL,
    time_created  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    comments TEXT,
    published_instant INTEGER,
    author VARCHAR,
    up_votes INTEGER,
    down_votes INTEGER,
    number_of_reads INTEGER,
    CONSTRAINT article_data_url_fk FOREIGN KEY(url) REFERENCES article(url) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS article_data_time_created_idx ON article_data (time_created DESC);