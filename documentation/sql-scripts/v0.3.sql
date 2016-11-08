ALTER TABLE `ad_8d9ad838f8c7d40`.`appointment` 
ADD COLUMN `new_appointment_text_sent_to_service_provider` BIT(1) NOT NULL DEFAULT 0 AFTER `cancellation_text_sent_to_service_provider`;
