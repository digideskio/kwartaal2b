
    declare
            l_passed boolean := true;
    begin
        if l_oper in ({{triggerOperator}})
        then
            l_passed := {{triggerPassed}};
            if not l_passed
            then
                l_error_stack := l_error_stack || '{{errorMessage}}';
            end if;
        end if;
    end;
