INSERT INTO users(username, password, enabled)
SELECT 'admin', '{noop}admin', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'admin');

INSERT INTO users(username, password, enabled)
SELECT 'jamescarter', '{noop}password', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'customer');

INSERT INTO users(username, password, enabled)
SELECT 'helenleary', '{noop}password', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'customer');

INSERT INTO users(username, password, enabled)
SELECT 'lindadouglas', '{noop}password', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'customer');

INSERT INTO users(username, password, enabled)
SELECT 'rafaelortega', '{noop}password', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'customer');

INSERT INTO users(username, password, enabled)
SELECT 'henrystevens', '{noop}password', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'customer');

INSERT INTO users(username, password, enabled)
SELECT 'sharonjenkins', '{noop}password', true
WHERE NOT EXISTS (SELECT * FROM users WHERE username = 'customer');

INSERT INTO roles (username, role)
SELECT 'admin', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 1);

INSERT INTO roles (username, role)
SELECT 'jamescarter', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO roles (username, role)
SELECT 'helenleary', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO roles (username, role)
SELECT 'lindadouglas', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO roles (username, role)
SELECT 'rafaelortega', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO roles (username, role)
SELECT 'henrystevens', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO roles (username, role)
SELECT 'sharonjenkins', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE id = 2);

INSERT INTO customers (name, surname, credit_limit, used_credit_limit, username)
VALUES ('James', 'Carter', 10000.00, 2000.00, 'jamescarter'),
       ('Helen', 'Leary', 15000.00, 3000.00, 'helenleary'),
       ('Linda', 'Douglas', 12000.00, 2500.00, 'lindadouglas'),
       ('Rafael', 'Ortega', 18000.00, 5000.00, 'rafaelortega'),
       ('Henry', 'Stevens', 13000.00, 1500.00, 'henrystevens'),
       ('Sharon', 'Jenkins', 14000.00, 3500.00, 'sharonjenkins');

-- Insert loan for James Carter: loan amount 5000.00, 6 installments, create date '2025-01-01'
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid)
SELECT id, 5000.00, 6, '2025-01-01', false
FROM customers
WHERE name = 'James'
  AND surname = 'Carter';

-- Insert loan for Helen Leary: loan amount 7000.00, 12 installments, create date '2025-02-01'
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid)
SELECT id, 7000.00, 12, '2025-02-01', false
FROM customers
WHERE name = 'Helen'
  AND surname = 'Leary';

-- Insert loan for Linda Douglas: loan amount 6000.00, 6 installments, create date '2025-03-01'
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid)
SELECT id, 6000.00, 6, '2025-03-01', false
FROM customers
WHERE name = 'Linda'
  AND surname = 'Douglas';

-- Insert loan for Rafael Ortega: loan amount 8000.00, 6 installments, create date '2025-04-01'
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid)
SELECT id, 8000.00, 6, '2025-04-01', false
FROM customers
WHERE name = 'Rafael'
  AND surname = 'Ortega';

-- Insert loan for Henry Stevens: loan amount 4000.00, 6 installments, create date '2025-05-01'
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid)
SELECT id, 4000.00, 6, '2025-05-01', false
FROM customers
WHERE name = 'Henry'
  AND surname = 'Stevens';

-- Insert loan for Sharon Jenkins: loan amount 9000.00, 6 installments, create date '2025-06-01'
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid)
SELECT id, 9000.00, 6, '2025-06-01', false
FROM customers
WHERE name = 'Sharon'
  AND surname = 'Jenkins';

-- Insert installments for James Carter's loan (5000.00 over 6 installments ≈ 833.33 each)
INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 833.33, 0.00, '2025-02-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'James'
  AND c.surname = 'Carter';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 833.33, 0.00, '2025-03-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'James'
  AND c.surname = 'Carter';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 833.33, 0.00, '2025-04-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'James'
  AND c.surname = 'Carter';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 833.33, 0.00, '2025-05-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'James'
  AND c.surname = 'Carter';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 833.33, 0.00, '2025-06-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'James'
  AND c.surname = 'Carter';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 833.33, 0.00, '2025-07-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'James'
  AND c.surname = 'Carter';


-- Insert installments for Helen Leary's loan (7000.00 over 12 installments ≈ 583.33 each)
INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-03-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-04-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-05-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-06-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-07-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-08-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-09-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-10-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-11-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2025-12-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2026-01-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 583.33, 0.00, '2026-02-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Helen'
  AND c.surname = 'Leary';


-- Insert installments for Linda Douglas's loan (6000.00 over 6 installments = 1000.00 each)
INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1000.00, 0.00, '2025-04-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Linda'
  AND c.surname = 'Douglas';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1000.00, 0.00, '2025-05-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Linda'
  AND c.surname = 'Douglas';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1000.00, 0.00, '2025-06-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Linda'
  AND c.surname = 'Douglas';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1000.00, 0.00, '2025-07-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Linda'
  AND c.surname = 'Douglas';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1000.00, 0.00, '2025-08-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Linda'
  AND c.surname = 'Douglas';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1000.00, 0.00, '2025-09-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Linda'
  AND c.surname = 'Douglas';


-- Insert installments for Rafael Ortega's loan (8000.00 over 6 installments ≈ 1333.33 each)
INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1333.33, 0.00, '2025-05-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Rafael'
  AND c.surname = 'Ortega';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1333.33, 0.00, '2025-06-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Rafael'
  AND c.surname = 'Ortega';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1333.33, 0.00, '2025-07-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Rafael'
  AND c.surname = 'Ortega';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1333.33, 0.00, '2025-08-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Rafael'
  AND c.surname = 'Ortega';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1333.33, 0.00, '2025-09-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Rafael'
  AND c.surname = 'Ortega';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1333.33, 0.00, '2025-10-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Rafael'
  AND c.surname = 'Ortega';


-- Insert installments for Henry Stevens's loan (4000.00 over 6 installments ≈ 666.67 each)
INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 666.67, 0.00, '2025-06-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Henry'
  AND c.surname = 'Stevens';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 666.67, 0.00, '2025-07-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Henry'
  AND c.surname = 'Stevens';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 666.67, 0.00, '2025-08-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Henry'
  AND c.surname = 'Stevens';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 666.67, 0.00, '2025-09-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Henry'
  AND c.surname = 'Stevens';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 666.67, 0.00, '2025-10-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Henry'
  AND c.surname = 'Stevens';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 666.67, 0.00, '2025-11-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Henry'
  AND c.surname = 'Stevens';


-- Insert installments for Sharon Jenkins's loan (9000.00 over 6 installments = 1500.00 each)
INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1500.00, 0.00, '2025-07-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Sharon'
  AND c.surname = 'Jenkins';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1500.00, 0.00, '2025-08-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Sharon'
  AND c.surname = 'Jenkins';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1500.00, 0.00, '2025-09-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Sharon'
  AND c.surname = 'Jenkins';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1500.00, 0.00, '2025-10-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Sharon'
  AND c.surname = 'Jenkins';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1500.00, 0.00, '2025-11-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Sharon'
  AND c.surname = 'Jenkins';

INSERT INTO loan_installments (loan_id, amount, paid_amount, due_date, is_paid)
SELECT l.id, 1500.00, 0.00, '2025-12-01', false
FROM loans l
         JOIN customers c ON l.customer_id = c.id
WHERE c.name = 'Sharon'
  AND c.surname = 'Jenkins';
