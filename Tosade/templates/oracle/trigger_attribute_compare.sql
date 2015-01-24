
    declare
        l_passed boolean := true;
		{{interEntityOne}}
    begin
        if l_oper in ({{triggerOperator}})
        then
			{{interEntityTwo}}
            l_passed := :new.{{fieldName}} {{operator}} {{operatorValue}};
            if not l_passed
            then
                l_error_stack := l_error_stack || '{{errorMessage}}';
            end if;
        end if;
    end;
