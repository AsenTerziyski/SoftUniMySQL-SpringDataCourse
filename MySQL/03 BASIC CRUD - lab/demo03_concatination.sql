select 
concat_ws(' + ', `first_name`, `last_name`) AS FULL_NAME,
`job_title` as `job_title`,
`id` as No
from `employees`;