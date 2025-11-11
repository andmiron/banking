CREATE TABLE IF NOT EXISTS accounts (
    id              BIGSERIAL   PRIMARY KEY,
    customer_id     BIGINT        NOT NULL,
    status          VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    country         CHAR(2)     NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT accounts_status_chk CHECK (status IN ('ACTIVE','BLOCKED','CLOSED'))
);

CREATE INDEX IF NOT EXISTS idx_accounts_customer_id ON accounts (customer_id);

CREATE TABLE IF NOT EXISTS currencies (
    code   CHAR(3)     PRIMARY KEY,
    name   VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS balances (
    account_id      BIGINT      NOT NULL REFERENCES accounts (id),
    status          VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    currency_code   CHAR(3)  NOT NULL REFERENCES currencies(code),
    available_amount NUMERIC(10, 4) NOT NULL DEFAULT 0,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (account_id, currency_code),
    CONSTRAINT balances_status_chk CHECK (status IN ('ACTIVE','BLOCKED','CLOSED'))
);

CREATE TABLE IF NOT EXISTS transactions (
    id              BIGSERIAL PRIMARY KEY,
    account_id      BIGINT      NOT NULL REFERENCES accounts (id),
    direction       VARCHAR(3)  NOT NULL,
    amount          NUMERIC(10, 4) NOT NULL,
    currency_code   CHAR(3)  NOT NULL REFERENCES currencies(code),
    description     TEXT        NOT NULL,
    balance_after   NUMERIC(10, 4) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT transactions_direction_chk CHECK (direction IN ('IN', 'OUT'))
);

CREATE INDEX IF NOT EXISTS idx_transactions_account_created_at
    ON transactions (account_id, created_at DESC);


INSERT INTO currencies (code, name)
    VALUES ('EUR', 'Euro'),
           ('SEK', 'Swedish Krona'),
           ('GBP', 'British Pound'),
           ('USD', 'United States Dollar');