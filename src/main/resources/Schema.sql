CREATE TABLE CARD(
    card_id varchar(30) NOT NULL,
    card_number varchar(50) NOT NULL,
    credit_line decimal  NOT NULL,
    last_digits varchar(4) NOT NULL,
    PRIMARY KEY (card_id)
);
CREATE TABLE USER(
  clip_id varchar(50) NOT NULL,
  name varchar(100) NOT NULL,
  card_id varchar(30) NOT NULL,
  pwd varchar(50) NOT NULL,
  PRIMARY KEY (clip_id),
  FOREIGN KEY (card_id) REFERENCES CARD(card_id)
);

CREATE TABLE DISBURSEMENT(
    disbursement_id bigint  NOT NULL,
    destination_user varchar(50) NOT NULL,
    amount decimal NOT NULL,
    date Timestamp  NOT NULL,
    PRIMARY KEY (disbursement_id),
    FOREIGN KEY (destination_user) REFERENCES USER(clip_id)
);
CREATE TABLE TRANSACTION(
    transaction_id bigint  NOT NULL,
    creation_user varchar(50) NOT NULL,
    destination_user varchar(50) NOT NULL,
    amount decimal NOT NULL,
    disbursement_id bigint,
    is_disbursement boolean NOT NULL,
    date Timestamp  NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (creation_user) REFERENCES USER(clip_id),
    FOREIGN KEY (destination_user) REFERENCES USER(clip_id),
    FOREIGN KEY (disbursement_id) REFERENCES DISBURSEMENT(disbursement_id)
);

