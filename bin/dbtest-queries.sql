
select sum(claimed_charge) from document;

select doc.insured_name,doc.insured_address,doc.claimed_charge from document as doc inner join batch as bac on doc.batch_id=bac.id 
where doc.status='TO_REPRICE' and bac.customer_id in (1,2);