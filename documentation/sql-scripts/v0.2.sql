ALTER TABLE `scheduler`.`appointment` 
ADD COLUMN `cancellation_text_sent_to_service_provider` BIT(1) NOT NULL DEFAULT 0 AFTER `updated_by`;
