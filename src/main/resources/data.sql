INSERT INTO salons (salon_id, salon_name, salon_address, salon_phone, salon_days_open)
VALUES
    (1, 'SalonA', '1233 street', '980809', '1111111'),
    (2, 'SalonB', '9023 Road', '12334', '1011111'),
    (3, 'Salon0na', '3242 bvard', '09865', '0000011');

INSERT INTO stylists (stylist_id, stylist_name, stylist_phone, stylist_salary, salon_id)
VALUES
    (1, 'Jean', '12323', 100, 1),
    (2, 'Paula', '123234', 100, 2),
    (3, 'Zack', '67577', 300, 1),
    (4, 'Amy', '123213214', 10, 3);