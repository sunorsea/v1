<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hello!${username}

    <hr>
    id:${student.id}
    name:${student.name}
    entryDate:${student.entryDate?datetime}
    <hr/>
    <table>
        <tr>
            <td>id</td>
            <td>name</td>
            <td>entryDate</td>
        </tr>
        <#list list as stu>
            <tr>
                <td>${stu.id}</td>
                <td>${stu.name}</td>
                <td>${stu.entryDate?date}</td>
            </tr>
        </#list>
    </table>
    <hr/>
    <#if (money>=1000000)>
        小康
        <#elseif (money>=100000)>
        脱贫
        <#else >
        去吃国家饭吧
    </#if>
    <hr>
    ${msg!}
    ${msg!'没有什么啊'}

</body>
</html>