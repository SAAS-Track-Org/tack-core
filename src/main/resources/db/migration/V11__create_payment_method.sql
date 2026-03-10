-- Tabela de métodos de pagamento disponíveis na plataforma
CREATE TABLE payment_method
(
    id         VARCHAR(50)  PRIMARY KEY,
    label      VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- Dados iniciais (alinhados com Paymentmethods.ts do frontend)
INSERT INTO payment_method (id, label) VALUES
    ('PIX',           'Pix'),
    ('CREDIT_CARD',   'Cartão de Crédito'),
    ('DEBIT_CARD',    'Cartão de Débito'),
    ('CASH',          'Dinheiro'),
    ('BANK_TRANSFER', 'Transferência Bancária'),
    ('MEAL_VOUCHER',  'Vale Refeição'),
    ('FOOD_VOUCHER',  'Vale Alimentação');

-- Tabela de junção AppUser <-> PaymentMethod
CREATE TABLE app_user_payment_method
(
    app_user_id       UUID        NOT NULL REFERENCES app_user(id),
    payment_method_id VARCHAR(50) NOT NULL REFERENCES payment_method(id),
    PRIMARY KEY (app_user_id, payment_method_id)
);

-- Remove coluna legada de string separada por vírgula
ALTER TABLE app_user DROP COLUMN payment_methods;

