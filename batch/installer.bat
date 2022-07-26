rem create SimComparisonTool folder is localappdata if it doesn't exist
if not exist %localappdata%\SimComparisonTool (mkdir %localappdata%\SimComparisonTool)

rem move ICON and TurboMap to sim comparison tool directory
copy .\content\ICON %localappdata%\SimComparisonTool
copy .\content\TurboMap %localappdata%\SimComparisonTool

pause