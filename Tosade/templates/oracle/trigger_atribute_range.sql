
declare
	l_passed boolean := true;
begin
	if l_oper({{triggerOperator}})
	then
		{{triggerPassed}}
	end if;
end;
