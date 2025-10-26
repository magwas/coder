<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs">

<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
    <xsl:message>Starting file generation with xsl:result-document...</xsl:message>
    
    <!-- Process each file element and write it to disk -->
    <xsl:for-each select="//file">
        <xsl:variable name="filename" select="@name"/>
        <xsl:variable name="content" select="text()"/>
        
        <xsl:message>Writing file: <xsl:value-of select="$filename"/></xsl:message>
        
        <xsl:result-document href="{$filename}" method="text" encoding="UTF-8">
            <xsl:value-of select="$content"/>
        </xsl:result-document>
    </xsl:for-each>
    
    <xsl:message>File generation complete!</xsl:message>
    
    <!-- Create a summary report -->
    <summary>
        <files-generated>
            <xsl:value-of select="count(//file)"/>
        </files-generated>
        <timestamp>
            <xsl:value-of select="current-dateTime()"/>
        </timestamp>
    </summary>
</xsl:template>

</xsl:stylesheet>
